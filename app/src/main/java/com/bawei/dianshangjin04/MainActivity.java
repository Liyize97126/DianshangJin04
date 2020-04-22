package com.bawei.dianshangjin04;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bawei.dianshangjin04.bean.UserBean;
import com.bawei.dianshangjin04.dao.DaoMaster;
import com.bawei.dianshangjin04.dao.DaoSession;
import com.bawei.dianshangjin04.dao.UserBeanDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    //定义
    private UserBeanDao userBeanDao;
    @BindView(R.id.show)
    protected TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定
        ButterKnife.bind(this);
        //初始化数据库
        DaoSession daoSession = DaoMaster.newDevSession(this, UserBeanDao.TABLENAME);
        userBeanDao = daoSession.getUserBeanDao();
    }
    @OnClick(R.id.insert1)
    protected void click01() {
        //插入数据（通过insert，不允许重复执行，如果主键id重复，则崩溃报错）
        //此处为避免报错，先执行条件查询
        //eq方法是==等于；like方法是模糊查询
        List<UserBean> list = userBeanDao.queryBuilder().where(UserBeanDao.Properties.Id.eq(1)).list();
        if(list.size() != 0){
            Toast.makeText(this,"数据已存在于数据库，不可重复添加！",Toast.LENGTH_LONG).show();
        } else {
            userBeanDao.insert(new UserBean(1,"李易泽",23));
            Toast.makeText(this,"成功插入一条数据！",Toast.LENGTH_LONG).show();
            Log.i("Tag","成功插入一条数据");
        }
    }
    @OnClick(R.id.insert2)
    protected void click02() {
        //插入或替换数据，允许重复，id重复之后数据会自动更新
        userBeanDao.insertOrReplace(new UserBean(1,"李易泽",23));
        Log.i("Tag","成功插入一条数据");
        Toast.makeText(this,"成功插入一条数据！",Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.insert3)
    protected void click03() {
        //插入列表数据，Tx代表事务
        //userBeanDao.insertInTx();也能加多个数据，但它和insert一样都是只能添加一次（数据表主键非自增），再执行时会报错
        userBeanDao.insertOrReplaceInTx(new UserBean(1,"李易泽",25),new UserBean(2,"李易泽2",27),
                new UserBean(3,"李易泽3",23),new UserBean(4,"李易泽4",28));
        Log.i("Tag","成功插入列表数据");
        Toast.makeText(this,"成功插入列表数据！",Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.delete1)
    protected void click04(){
        //删除一条数据
        try {
            userBeanDao.delete(new UserBean(1,"李易泽",23));
            Toast.makeText(this,"成功删除一条数据！",Toast.LENGTH_LONG).show();
            Log.i("Tag","成功删除一条数据");
        } catch (Exception e){
            Toast.makeText(this,"应用发生异常！",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    @OnClick(R.id.delete2)
    protected void click05(){
        //删除所有数据
        try {
            userBeanDao.deleteAll();
            Toast.makeText(this,"成功删除全部数据！",Toast.LENGTH_LONG).show();
            Log.i("Tag","成功删除全部数据");
        } catch (Exception e){
            Toast.makeText(this,"应用发生异常！",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    @OnClick(R.id.update)
    protected void click06(){
        //修改一条数据
        try {
            userBeanDao.update(new UserBean(1,"李易泽",19));
            Toast.makeText(this,"成功修改一条数据！",Toast.LENGTH_LONG).show();
            Log.i("Tag","成功修改一条数据");
        } catch (Exception e){
            Toast.makeText(this,"应用发生异常！",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    @OnClick(R.id.query1)
    protected void click07(){
        //查询一条数据
        UserBean userBean = userBeanDao.loadByRowId(3);
        if(userBean != null){
            show.setText("ID：" + userBean.getId() + "\n" +
                    "姓名：" + userBean.getName() + "\n" +
                    "年龄：" + userBean.getAge());
        } else {
            show.setText("未查询到任何数据！");
        }
    }
    @OnClick(R.id.query2)
    protected void click08(){
        //查询全部数据，通过loadAll
        List<UserBean> userBeans = userBeanDao.loadAll();
        StringBuffer stringBuffer = new StringBuffer();
        if(userBeans.size() != 0){
            for (UserBean userBean : userBeans) {
                stringBuffer.append("ID：" + userBean.getId() + "\n" +
                        "姓名：" + userBean.getName() + "\n" +
                        "年龄：" + userBean.getAge() + "\n========================\n");
            }
            stringBuffer.append("数据已全部加载完毕！");
            show.setText(stringBuffer.toString());
        } else {
            show.setText("未查询到任何数据！");
        }
    }
}
