# AndroidMVVM
Android MVVM是一款基于MVVM框架，以Jetpack组件DataBinding+LiveData+ViewModel为基础，整合Retrofit+RxJava网络模块的快速开发框架。

## 框架流程
![](./img/fc.png)

## 框架特点
- **Jetpack组件**

	1. ViewBinding & DataBinding
	2. Lifecycles
	3. LiveData
	4. Navigation
	5. Paging
	6. Room
    7. ViewModel

- **流行框架**

	1. [retrofit](https://github.com/square/retrofit)+[okhttp](https://github.com/square/okhttp)+[rxJava](https://github.com/ReactiveX/RxJava)负责网络请求
	2. [gson](https://github.com/google/gson)负责解析json数据
    3. [glide](https://github.com/bumptech/glide)负责加载图片；

- **基类封装**

	1. BaseActivity
	2. BaseFragment
    3. BaseViewModel

- **全局操作**

	1. 全局的Activity堆栈式管理
	2. LoggingInterceptor全局拦截网络请求日志
   	3. 全局的异常捕获，程序发生异常时不会崩溃，返回上个界面。
   	4. 使用androidx
   	5. 不使用kotlin

- **Paging组件**

    1. 实现了Network only 和 Network & database 两种模式
    ![](./img/paging.png)

## 界面

    1. 登录界面（使用任意账户登录）
    2. 我的仓库列表
    3. 我的star仓库列表
    4. 我的following列表
    5. 仓库详情
    6. 用户详情

## 注意

    1. 接口使用GitHub API v3，单IP限制每小时60次requests
