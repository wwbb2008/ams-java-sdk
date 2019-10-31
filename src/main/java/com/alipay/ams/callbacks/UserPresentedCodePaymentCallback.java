/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.ams.callbacks;

import java.io.IOException;
import java.util.HashMap;

import com.alipay.ams.AMSClient;
import com.alipay.ams.domain.Callback;
import com.alipay.ams.domain.ResponseHeader;
import com.alipay.ams.domain.ResponseResult;
import com.alipay.ams.domain.requests.PaymentInquiryRequest;
import com.alipay.ams.domain.requests.UserPresentedCodePaymentRequest;
import com.alipay.ams.domain.responses.UserPresentedCodePaymentResponse;

/**
 * 
 * @author guangling.zgl
 * @version $Id: UserPresentedCodePaymentCallback.java, v 0.1 2019年8月26日 下午6:27:20 guangling.zgl Exp $
 */
public class UserPresentedCodePaymentCallback
                                             extends
                                             Callback<UserPresentedCodePaymentRequest, UserPresentedCodePaymentResponse> {

    private PaymentInquiryCallback paymentInquiryCallback;

    /**
     * @param paymentInquiryCallback
     */
    public UserPresentedCodePaymentCallback(PaymentInquiryCallback paymentInquiryCallback) {
        this.paymentInquiryCallback = paymentInquiryCallback;
    }

    /** 
     * @see com.alipay.ams.domain.Callback#onIOException(java.io.IOException, com.alipay.ams.AMSClient, com.alipay.ams.domain.Request)
     */
    @Override
    public void onIOException(IOException e, AMSClient client,
                              UserPresentedCodePaymentRequest paymentRequest) {
        //Initiate a Inquiry
        client.execute(
            PaymentInquiryRequest.byPaymentRequestId(client.getSettings(),
                paymentRequest.getPaymentRequestId(), paymentRequest.getAgentToken()),
            paymentInquiryCallback);

    }

    /** 
     * @see com.alipay.ams.domain.Callback#onHttpStatusNot200(com.alipay.ams.AMSClient, com.alipay.ams.domain.Request, int)
     */
    @Override
    public void onHttpStatusNot200(AMSClient client, UserPresentedCodePaymentRequest request,
                                   int code) {
        //Initiate a Inquiry
        client.execute(
            PaymentInquiryRequest.byPaymentRequestId(client.getSettings(),
                request.getPaymentRequestId(), request.getAgentToken()), paymentInquiryCallback);
    }

    /** 
     * @see com.alipay.ams.domain.Callback#onFstatus(com.alipay.ams.AMSClient, com.alipay.ams.domain.Request, com.alipay.ams.domain.ResponseResult)
     */
    @Override
    public void onFstatus(AMSClient client, UserPresentedCodePaymentRequest request,
                          ResponseResult responseResult) {
        paymentInquiryCallback.getPaymentCancelCallback().getPaymentStatusUpdateCallback()
            .onPaymentFailed(request.getPaymentRequestId(), responseResult);
    }

    /** 
     * @see com.alipay.ams.domain.Callback#onUstatus(com.alipay.ams.domain.ResponseResult, com.alipay.ams.AMSClient, com.alipay.ams.domain.Request)
     */
    @Override
    public void onUstatus(AMSClient client, UserPresentedCodePaymentRequest paymentRequest,
                          ResponseResult responseResult) {
        //Initiate an Inquiry
        client.execute(
            PaymentInquiryRequest.byPaymentRequestId(client.getSettings(),
                paymentRequest.getPaymentRequestId(), paymentRequest.getAgentToken()),
            paymentInquiryCallback);

    }

    /** 
     * @see com.alipay.ams.domain.Callback#onSstatus(com.alipay.ams.AMSClient, java.lang.String, com.alipay.ams.domain.ResponseHeader, java.util.HashMap, com.alipay.ams.domain.Request)
     */
    @Override
    public void onSstatus(AMSClient client, String requestURI, ResponseHeader responseHeader,
                          HashMap<String, Object> body, UserPresentedCodePaymentRequest request) {

        UserPresentedCodePaymentResponse paymentResponse = new UserPresentedCodePaymentResponse(
            client.getSettings(), requestURI, responseHeader, body);

        paymentInquiryCallback.getPaymentCancelCallback().getPaymentStatusUpdateCallback()
            .handlePaymentSuccess(paymentResponse.getPaymentResultModel());

    }

}