package org.example.util.tree;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.base.vo.TreeModel;
import org.example.error.exception.CommonException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lihui
 * @since 2023/4/3
 */
@Slf4j
public class TreeModelUtils {
    /**
     * 构建树形结构
     *
     * @param objects
     * @return
     */
    public static List<TreeModel> buildTreeModel(Collection<?> objects) {
        List<TreeModel> treeModels = new ArrayList<>();
        List<TreeModel> resultTreeModels = new ArrayList<>();
        for (Object object : objects) {
            TreeModel treeModel = new TreeModel();
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                TreeModelField annotation = field.getAnnotation(TreeModelField.class);
                if (annotation == null) {
                    continue;
                }
                try {
                    switch (annotation.field()) {
                        case ID:
                            treeModel.setId(String.valueOf(field.get(object)));
                            break;
                        case LABEL:
                            treeModel.setLabel(String.valueOf(field.get(object)));
                            break;
                        case PARENT_ID:
                            treeModel.setParentId(String.valueOf(field.get(object)));
                            break;
                    }
                } catch (Exception e) {
                    log.error("build tree model error:{}", e.getMessage());
                }
            }
            treeModels.add(treeModel);
        }
        // 获取全部id集合
        Set<String> ids = treeModels.stream().map(TreeModel::getId).collect(Collectors.toSet());
        for (TreeModel treeModel : treeModels) {
            // 所属的parentId在id集合中不存在，即为根节点
            if (!ids.contains(treeModel.getParentId())) {
                resultTreeModels.add(treeModel);
                buildChildren(treeModels, treeModel);
            }
        }
        return resultTreeModels;
    }

    private static void buildChildren(List<TreeModel> treeModels, TreeModel treeModel) {
        List<TreeModel> children = treeModels.stream().filter(o -> o.getParentId().equals(treeModel.getId())).collect(Collectors.toList());
        treeModel.setChildren(children);
        for (TreeModel tm : children) {
            buildChildren(treeModels, tm);
        }
    }

    /**
     * 构建任意类型树形结构
     *
     * @param objects
     * @param <T>
     * @return
     */
    public static <T> List<T> buildObjectTree(Collection<T> objects) {
        List<T> resultObjectTrees = new ArrayList<>();
        Set<Object> ids = new HashSet<>();
        for (T object : objects) {
            List<Field> fields = new ArrayList<>();
            recursionSuperClassField(fields, object.getClass());
            for (Field field : fields) {
                field.setAccessible(true);
                TreeModelField annotation = field.getAnnotation(TreeModelField.class);
                if (annotation == null) {
                    continue;
                }
                try {
                    if (annotation.field() == TreeModelFieldEnum.ID) {
                        ids.add(field.get(object));
                        break;
                    }
                } catch (Exception e) {
                    log.error("build object tree error:{}", e.getMessage());
                }
            }
        }
        for (T object : objects) {
            List<Field> fields = new ArrayList<>();
            recursionSuperClassField(fields, object.getClass());
            for (Field field : fields) {
                TreeModelField annotation = field.getAnnotation(TreeModelField.class);
                if (annotation == null) {
                    continue;
                }
                try {
                    if (annotation.field() == TreeModelFieldEnum.PARENT_ID) {
                        field.setAccessible(true);
                        // 所属的parentId在id集合中不存在，即为根节点
                        if (!ids.contains(field.get(object))) {
                            resultObjectTrees.add(object);
                            buildChildren(objects, object);
                        }
                    }
                } catch (Exception e) {
                    log.error("build object tree error:{}", e.getMessage());
                }
            }
        }
        return resultObjectTrees;
    }

    private static void recursionSuperClassField(List<Field> fields, Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> superclass = clazz.getSuperclass();
        recursionSuperClassField(fields, superclass);
    }

    private static <T> void buildChildren(Collection<T> objectTrees, T object) {
        Field childrenField = null;
        Object parentObjectId = null;
        List<T> children = new ArrayList<>();
        List<Field> fields = new ArrayList<>();
        recursionSuperClassField(fields, object.getClass());
        for (Field field : fields) {
            TreeModelField annotation = field.getAnnotation(TreeModelField.class);
            if (annotation == null) {
                continue;
            }
            try {
                if (annotation.field() == TreeModelFieldEnum.ID) {
                    field.setAccessible(true);
                    parentObjectId = field.get(object);
                }
                if (annotation.field() == TreeModelFieldEnum.CHILDREN) {
                    childrenField = field;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        for (T objectTree : objectTrees) {
            fields.clear();
            recursionSuperClassField(fields, objectTree.getClass());
            for (Field field : fields) {
                TreeModelField annotation = field.getAnnotation(TreeModelField.class);
                if (annotation == null) {
                    continue;
                }
                try {
                    if (annotation.field() == TreeModelFieldEnum.PARENT_ID) {
                        field.setAccessible(true);
                        Object parentId = field.get(objectTree);
                        if (parentId.equals(parentObjectId)) {
                            children.add(objectTree);
                            break;
                        }
                    }
                } catch (Exception e) {
                    log.error("build object tree error:{}", e.getMessage());
                }
            }
        }
        if (childrenField == null) {
            throw new CommonException("build object tree error, not found children field");
        }
        try {
            childrenField.setAccessible(true);
            childrenField.set(object, children);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (T t : children) {
            buildChildren(children, t);
        }
    }
}