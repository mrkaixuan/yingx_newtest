package com.hkx.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 通用返回结果,也就是一个实体类,
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 用于封装返回的json数据
 */
@Component
@Scope("prototype")
public class CommonResult {
    private String message;
    private String status;
    private Object data;

//这样做的目的是为了在service层返回信息的时候,只需要new CommonResult().success("","","")
    public CommonResult success(String status,String message,Object data){
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus(status);
        commonResult.setMessage(message);
        commonResult.setData(data);
        return commonResult;
    }

    public CommonResult success(String status,Object data){
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus(status);
        commonResult.setMessage("请求成功");
        commonResult.setData(data);
        return commonResult;
    }
    public CommonResult success(Object data){
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("100");
        commonResult.setMessage("请求成功");
        commonResult.setData(data);
        return commonResult;
    }

    //失败情况下没有data数据,直接返回响应的信息message和status 没有data数据,是null
    public CommonResult failed(String status,String message){
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus(status);
        commonResult.setMessage(message);
        commonResult.setData(null);
        return commonResult;
    }

    public CommonResult failed(String message){
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("104");
        commonResult.setMessage(message);
        commonResult.setData(null);
        return commonResult;
    }

    public CommonResult failed(){
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("104");
        commonResult.setMessage("请求失败");
        commonResult.setData(null);
        return commonResult;
    }
}
