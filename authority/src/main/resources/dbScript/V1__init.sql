
-- 默认用户名密码root/root
# mysql -u root -p
-- 连接
# mysql -h192.168.43.252 -uroot -p111111

-- 建库
#create database test;

-- 查看用户
#select host,user,password,grant_priv,Super_priv from mysql.user;
-- 新建用户
#CREATE USER  'test'@'localhost'  IDENTIFIED BY  '123456';
-- 删除用户
#drop user 'test'@'host';
-- 授权用户
#grant all on test.* to 'test'@'localhost' IDENTIFIED BY  '123456';


-- 建表语句事例

create table sys_user(
    id bigint primary key,#部门编号 整形 主键 自增长
    name varchar(100),#部门名称
    password varchar(100),#密码
    address varchar(100),
    money DECIMAL
);








