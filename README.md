# DeepCalendar
使用DeepCalendar可以方便用户更加便捷的操作android 日历功能

## 使用

gradle依赖
```
compile 'com.deep:deep_insertcalendar:1.0'
```

## 获取当前日历账户

```
              ArrayList<Account> list =  DeepCalendarUtil.getAccount(MainActivity.this);

```

## 插入日历账户
现在的android系统插入事项的前提是需要有个本地的账户或者google或者rom厂商的账户。当然也可以自己插入账户：

```
 Account account = new Account();
                account.name = "aaq";
                account.account_name = "aaq";
                account.calendar_displayName = "ccc";
                account.account_type = "test";
                DeepCalendarUtil.addAccount(MainActivity.this,account);
```
## 插入待办事项


### 不指定账户插入
这是会在系统账户中选择优先级最高的进行插入：

```
 Clock clock = new Clock("123", "456");
 DeepCalendarUtil.addCalendarEvent(MainActivity.this, clock);
```
其中123是title，456是描述，如果设置闹钟或者起止时间可以使用

```
Clock clock = new Clock("123", "456");
clock.hasclock = 1;
clock.dtstart = System.currentTimeMillis();
clock.dtend = System.currentTimeMillis()+3600000;
DeepCalendarUtil.addCalendarEvent(MainActivity.this, clock);
```

### 指定账户插入

```
Clock clock = new Clock("123","456","1");
DeepCalendarUtil.addCalendarEvent(MainActivity.this,clock);
```
其中1为Account的id，可以通过前面提到过的getAccount进行获取，或者自己插入。

## 删除代办事项

```
 DeepCalendarUtil.deleteCalendarEvent(MainActivity.this,"aaaa");
```
其中aaaa为事项的title。