# HotServer
完整实现servlet以及SPI规范

## 目标功能
 - [x] http1.1
 - [x] 部分servlet
 - [x] SPI规范
 - [ ] 使用NIO模型提高性能
 - [ ] 完整的servlet
 - [ ] 可加载web.xml
 - [ ] 动态类加载,无需重启热更新类文件
 - [ ] cookie实现
 - [ ] session实现

## 运行截图
war包内的SpringBoot 项目的 Controller
<img width="1296" alt="image" src="https://user-images.githubusercontent.com/46882634/165488813-d0505979-6f47-4121-9fd4-2de0aa5aebaa.png">
HotServer根据SPI规范启动SpringBoot项目
<img width="1439" alt="image" src="https://user-images.githubusercontent.com/46882634/165488947-adbe03fb-83d0-4ed5-bce3-577476584a46.png">
访问测试
<img width="1032" alt="image" src="https://user-images.githubusercontent.com/46882634/165489030-fdc3b718-b213-483d-9810-8c926bb7e223.png">
