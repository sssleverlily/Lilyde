# Lilyde
基于fresco和glide封装的图片库，
实现了什么呢？

1、由于要桥接fresco, 使用了扩展函数，封装了常用的图片加载方法

2、自定义了glidemodule, 改变磁盘和线程池，加上内存自定义自己想要的加载效果

3、内存缓存的动态优化

4、使用glide加载图片，并利用FrameSequence来进行动图解码，由串行改成了并行，提升了加载效率

5、利用了Kotlin协程强大的 可取消的非阻塞式异步编程和对线程最大化利用的特性

