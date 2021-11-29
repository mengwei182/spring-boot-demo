DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `id` VARCHAR(32) NOT NULL,
    `parent_id` VARCHAR(255) COMMENT '父级ID',
    `title` VARCHAR(255) NOT NULL COMMENT '菜单名称',
    `level` INT NOT NULL DEFAULT 0 COMMENT '菜单级数',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '菜单排序',
    `name` VARCHAR(255) NOT NULL COMMENT '前端名称',
    `icon` VARCHAR(255) NOT NULL COMMENT '菜单图标',
    `hidden` TINYINT NOT NULL DEFAULT 0 COMMENT '是否隐藏',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT '菜单信息表';
INSERT INTO menu(id, title, name, icon)
VALUES (REPLACE(UUID(), '-', ''), '菜单管理', 'menu_manage', 'menu_manage_icon'),
       (REPLACE(UUID(), '-', ''), '用户管理', 'user_manage', 'user_manage_icon'),
       (REPLACE(UUID(), '-', ''), '角色管理', 'role_manage', 'role_manage_icon'),
       (REPLACE(UUID(), '-', ''), '资源管理', 'resource_manage', 'resource_manage_icon');

DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`
(
    `id` VARCHAR(32) NOT NULL,
    `name` VARCHAR(255) COMMENT '资源名称',
    `url` VARCHAR(255) NOT NULL COMMENT '资源URL',
    `description` TEXT COMMENT '描述',
    `category_id` VARCHAR(255) COMMENT '资源分类UUID',
    `enable` TINYINT NOT NULL DEFAULT 1 COMMENT '是否启用',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT '资源信息表';
INSERT INTO resource(id, url)
VALUES ('fc8d8a28f2b111ebad8f005056c00001', '/user/**'),
       ('fc8d9efdf2b111ebad8f005056c00001', '/menu/**'),
       ('fc8da005f2b111ebad8f005056c00001', '/resource/**'),
       ('fc8da05cf2b111ebad8f005056c00001', '/role/**');

DROP TABLE IF EXISTS `resource_category`;
CREATE TABLE `resource_category`
(
    `id` VARCHAR(32) NOT NULL,
    `name` VARCHAR(255) NOT NULL COMMENT '分类名称',
    `sort` INT NOT NULL COMMENT '排序',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT '资源分类信息表';

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id` VARCHAR(32) NOT NULL,
    `name` VARCHAR(255) NOT NULL COMMENT '资源名称',
    `description` TEXT NOT NULL COMMENT '描述',
    `sort` INT NOT NULL COMMENT '排序',
    `enable` TINYINT NOT NULL DEFAULT 1 COMMENT '是否启用',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT '资源信息表';
INSERT INTO role(id, name, description, sort)
VALUES ('ab1a320bf2ad11ebad8f005056c00001', '超级管理员', '拥有系统所有权限的角色', 0);

DROP TABLE IF EXISTS `role_menu_relation`;
CREATE TABLE `role_menu_relation`
(
    `id` VARCHAR(32) NOT NULL,
    `role_id` VARCHAR(255) NOT NULL COMMENT '角色表外键',
    `menu_id` VARCHAR(255) NOT NULL COMMENT '菜单表外键',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT '角色菜单信息表';

DROP TABLE IF EXISTS `role_resource_relation`;
CREATE TABLE `role_resource_relation`
(
    `id` VARCHAR(32) NOT NULL,
    `role_id` VARCHAR(255) NOT NULL COMMENT '角色表外键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源表外键',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT '角色资源信息表';
INSERT INTO role_resource_relation(id, role_id, resource_id)
VALUES (REPLACE(UUID(), '-', ''), 'ab1a320bf2ad11ebad8f005056c00001', 'fc8d8a28f2b111ebad8f005056c00001'),
       (REPLACE(UUID(), '-', ''), 'ab1a320bf2ad11ebad8f005056c00001', 'fc8d9efdf2b111ebad8f005056c00001'),
       (REPLACE(UUID(), '-', ''), 'ab1a320bf2ad11ebad8f005056c00001', 'fc8da005f2b111ebad8f005056c00001'),
       (REPLACE(UUID(), '-', ''), 'ab1a320bf2ad11ebad8f005056c00001', 'fc8da05cf2b111ebad8f005056c00001');

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id` VARCHAR(32) NOT NULL,
    `username` VARCHAR(255) NOT NULL COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码',
    `icon` VARCHAR(255) COMMENT '头像',
    `email` VARCHAR(255) COMMENT '邮箱',
    `nick_name` VARCHAR(255) COMMENT '昵称',
    `note` VARCHAR(255) COMMENT '个人说明',
    `enable` TINYINT NOT NULL DEFAULT 1 COMMENT '是否启用',
    `login_time` TIMESTAMP COMMENT '登录时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT '用户信息表';
INSERT INTO `user`(id, username, password, icon, email, nick_name, note)
VALUES ('17b90220c4104392aec7cccf9c8bcaef', 'superadmin', '$2a$10$s3Ii4pZfN8BjeGaUhgGQC.HqspWjjBGNl81OuwZk0UjA27dB.rrLC', 'super_icon', 'super@test.com', '超级管理员', '超级管理员');

DROP TABLE IF EXISTS `user_role_relation`;
CREATE TABLE `user_role_relation`
(
    `id` VARCHAR(32) NOT NULL,
    `user_id` VARCHAR(255) NOT NULL COMMENT '用户ID',
    `role_id` VARCHAR(255) NOT NULL COMMENT '角色ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT '用户角色信息表';
INSERT INTO user_role_relation(id, user_id, role_id)
VALUES (REPLACE(UUID(), '-', ''), '17b90220c4104392aec7cccf9c8bcaef', 'ab1a320bf2ad11ebad8f005056c00001');