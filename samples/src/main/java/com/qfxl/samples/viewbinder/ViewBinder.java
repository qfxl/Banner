package com.qfxl.samples.viewbinder;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class ViewBinder {

    public static void bind(final Activity activity) {
        Class clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(BindView.class)) {
                int viewId = field.getAnnotation(BindView.class).value();
                try {
                    Method findView = clazz.getMethod("findViewById", int.class);
                    View view = (View) findView.invoke(activity, viewId);
                    field.setAccessible(true);
                    field.set(activity, view);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (final Method method : methods) {
            if (method.isAnnotationPresent(OnClick.class)) {
                int viewId = method.getAnnotation(OnClick.class).value();
                try {
                    Method findView = clazz.getMethod("findViewById", int.class);
                    final View view = (View) findView.invoke(activity, viewId);
                    if (view != null) {
                        method.setAccessible(true);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Type[] types = method.getGenericParameterTypes();
                                if (types.length == 0) {
                                    try {
                                        method.invoke(activity);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                } else if (types.length == 1) {
                                    try {
                                        method.invoke(activity, view);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
