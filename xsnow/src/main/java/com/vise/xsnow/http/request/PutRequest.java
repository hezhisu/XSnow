package com.vise.xsnow.http.request;

import android.content.Context;

import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.mode.CacheResult;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * @Description: Put请求
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-04-28 16:06
 */
public class PutRequest extends BaseRequest<PutRequest> {
    @Override
    protected <T> Observable<T> execute(Type type) {
        return apiService.put(suffixUrl, params).compose(this.<T>norTransformer(type));
    }

    @Override
    protected <T> Observable<CacheResult<T>> cacheExecute(Type type) {
        return this.<T>execute(type).compose(ViseHttp.getInstance().getApiCache().<T>transformer(cacheMode, type));
    }

    @Override
    protected <T> void execute(Context context, ACallback<T> callback) {
        if (isLocalCache) {
            this.cacheExecute(getSubType(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
        }
        this.execute(getType(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }
}
