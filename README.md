     
      
> 版权所有，转载请注明出处 [https://kimxu.herokuapp.com/posts/android_touch_event/](https://kimxu.herokuapp.com/posts/android_touch_event/)
      
> 老生常谈的一个话题，Android触碰机制，虽然基础，但是很重要，尤其是涉及到复杂界面交互
> 的时候。当时自己看了很多篇blog，也是似懂非懂的，写代码，最重要的就是实践，自己多写写demo，
> 在去看看别人的blog思想，才会有醍醐灌顶，豁然开朗的感觉。废话不多说，今天这篇文章主要介绍
> Android触碰机制，以及自己在开发中遇见相关问题的处理方式。


# 触碰机制过程

Android触碰机制的过程整体思想，就是上级传递给下级。上级把需要办理的事情传递给下级，
下级进行思考对事情进行技术评估，如果没有能力进行处理，那么就返回给上级，让上级自行处理。
如果有能力处理，就拦下自己进行处理。

可以想成:

顾客去店里吃饭，点了一道菜，服务员把菜单拿给厨师长让厨师长做。厨师长进行思考，对这道菜进行
评估想是否自己去做这道菜，如果觉得这道菜有难度，那么就自己去做了。如果觉得这道菜很简单，就派实习厨师去做，
实习厨师进行思考，然后对这道菜进行评估，如果觉得自己能做，那么就把活拦下了。如果做不了，
就反馈给厨师长，让厨师长做。


涉及到代码的话就是：

顾客去店里吃饭，点了一道菜，服务员把菜单拿（dispatchTouchEvent）给厨师长让厨师长做。
厨师长进行思考（dispatchTouchEvent），对这道菜进行评估想是否自己去做这道菜（onInterceptTouchEvent），
如果觉得这道菜有难度，那么就自己去做了（onTouchEvent）。如果觉得这道菜很简单，就派实习厨师去做，
实习厨师进行思考（dispatchTouchEvent），如果觉得自己能做，那么就把活拦下了
（onTouchEvent）。如果做不了，就反馈给厨师长，让厨师长做。

这里要注意的是，服务员不会做饭，所以没有评估（onInterceptTouchEvent）的能力，实习厨师
也没有对自己能否完成这道菜有评估的能力（onInterceptTouchEvent）。

如果实习厨师可以完成任务的情况：
![](http://ww2.sinaimg.cn/large/006tNc79gw1fantfezx0ij30og05y40g.jpg)
实习厨师能做，那么所有事皆大欢喜。

如果实习厨师不能完成任务的情况：
![](http://ww1.sinaimg.cn/large/006tNc79gw1fanto1w7p8j30oc0aegp3.jpg)

如果实习厨师不能完成，那么就反馈给他的上一级，上一级在进行是否做的处理，如果最后都不想做，那么就
反馈给服务员，服务员告诉顾客，这道菜做不了。那么以后再有人需要做菜的时候，服务员就来脾气了，直接告诉
顾客做不了。

![](http://ww1.sinaimg.cn/large/006tNc79gw1fantkpq418j30o80cyn1c.jpg)
如果实习厨师不能完成，那么就反馈给他的上一级，上一级在进行是否做的处理，最后厨师长把菜做了，以后
再有菜需要做，厨师长就觉得手下都不行，别耽误时间了，自己做吧。

以上就是Android触碰机制的抽象思考。


# 代码实现

这里贴出一部分代码，需要完整代码，可以点击下面的*源码点我*。

服务员：

``` Java
private static final String TAG = "服务员";
@Override
public boolean onTouchEvent(MotionEvent event) {
    boolean action = super.onTouchEvent(event);
    Log.i(TAG, "这道菜做不了 " + action + Utils.getEvent(event.getAction()));
    return action;
}

@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    Log.i(TAG, "传递一个菜单 " + Utils.getEvent(ev.getAction()));
    return super.dispatchTouchEvent(ev);
}
```

厨师长1（为了流程更长一些，设定有两个厨师长）：

``` Java
private static String TAG = "厨师长";
@Override
public boolean onInterceptTouchEvent(MotionEvent ev) {
    boolean action =super.onInterceptTouchEvent(ev);
    Log.e(TAG, "是否自己去做？ " + action + Utils.getEvent(ev.getAction()));
    return action;
}


@Override
public boolean onTouchEvent(MotionEvent event) {
    boolean action =super.onTouchEvent(event);
    Log.e(TAG, "自己做 " + action + Utils.getEvent(event.getAction()));
    return action;
}


@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    Log.e(TAG, "进行思考 " + Utils.getEvent(ev.getAction()));
    return super.dispatchTouchEvent(ev);
}
```

厨师长2如果厨师长1的代码实现。


实习厨师：

``` Java
private static final String TAG = "实习厨师";
@Override
public boolean onTouchEvent(MotionEvent event) {
    boolean action =false;
    Log.w(TAG, "自己去做 " + action + Utils.getEvent(event.getAction()));
    return action;
}

@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    Log.w(TAG, "进行思考 " + Utils.getEvent(ev.getAction()));
    return super.dispatchTouchEvent(ev);
}

```


# 注意事项


一般来说，我们不实现代码的时候，不需要重写*dispatchTouchEvent*方法，因为该方法需要调用
*super.dispatchTouchEvent(ev)* 进行往下传递。如果直接返回True或者False，就不能往下传递了，
如果返回True，那么就是在思考，没有往下进行实践传递。

![](http://ww1.sinaimg.cn/large/006tNc79gw1fanu45iog7j30p006smz8.jpg)

如果返回False，那么就告诉上一级，做不了，不用往下传递实践了，你们上一级进行处理吧。

![](http://ww3.sinaimg.cn/large/006tNc79gw1fanu5sq210j30p808m0vq.jpg)


- - -
一次完成的事件响应是 onDown(1次) ---> onMove(n次（n>=0）) ---> onUp(1次)，如果其中的
某一事件被拦截了，那么在进行事件后续处理的时候，还是被该拦截处理。


## View中的事件监听
View 中的onTouchEvent 与 setOnTouchListener、setOnClickListener事件：
具体实现代码，可以分析源码，这里文章篇幅原因，只给出结论。

1. *setOnTouchListener*先于*onTouchEvent*事件调用，如果*setOnTouchListener*中
的*onTouch*方法返回true,那么*onTouchEvent*将不会执行。

2. 如果设置*setOnTouchListener*方法，如果*onTouch*方法返回true，那么将不会执行*onClick*
方法。如果返回false，会执行*onClick*方法。

3. 如果重写了*onTouchEvent*方法，那么*setOnclickListener*方法将不会被执行。
如果需要使用*onTouchEvent*还需要点击事件，那么可以使用下面的代码。

``` Java
public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final int moveX = (int)event.getX();
		final int scape = moveX - firstX;
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			firstX = (int) event.getX();//按下的时候开始的x的位置
			break;
		case MotionEvent.ACTION_MOVE:
			if (isMove) {
				move(scape);
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			
		case MotionEvent.ACTION_UP:
			secondX = (int)event.getX();//up的时候x的位置
			int distance = secondX - firstX;
			if (distance == 0) {
				//当没有变化的时候什么都不做
			}else {
				//执行move滑动后的操作
			}
			break;
		}
		return true;
	}

```



## CheckBox 的选中事件
*CheckBox* 有setOnCheckedChangeListener和setOnClickListener监听方法，如果想要
实现：只监听用户手动切换CheckBox事件，而不想要监听setChecked(),那么就实现

``` Java
checkBox.setOnClickListener(new OnClickListener(){

    @Override
    public void onClick(View checkBox) {
        // TODO Auto-generated method stub
        if(((CheckBox)checkBox).isChecked()){
            //已经选中
            ((CheckBox)checkBox).setChecked(false);
        }else {
            ((CheckBox)checkBox).setChecked(true);
        }
    }

});
```



# 总结

本篇博客到这里就结束了, [更多点我](https://kimxu.herokuapp.com/posts/android_touch_event/),如果有什么错误的地方，欢迎指点~v~












