# AndroidMVVM
Android MVVM是一款基于MVVM框架，以Jetpack组件DataBinding+LiveData+ViewModel为基础，整合Retrofit+Okhttp+RxJava+Glide等主流模块的快速开发框架。

## 框架流程
![](./img/fc.png)

## 框架特点
- **Jetpack组件**

	1. DataBinding
	2. LiveData
    3. ViewModel

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

## 界面

    1. DB+RecyclerView
    2. Network+RecyclerView

## 注意

    1.登录接口不支持调试
    2.其它接口使用github api