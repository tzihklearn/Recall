package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.*;
import com.example.recallbackend.pojo.dto.result.AnniversaryResult;
import com.example.recallbackend.pojo.dto.result.UserResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ChildHomepageService {

    CommonResult<UserResult> getName(Integer userId);

    CommonResult<String> changeUserName(NameParam nameParam);

    CommonResult<String> binding(QRCodeParam qrCodeParam);

    CommonResult<List<UserResult>> getAllBinding(Integer userId);

    CommonResult<String> setParentName(ChangeNameParam changeNameParam);

    CommonResult<String> unbinding(RelationParam relationParam);

    CommonResult<String> setAnniversary(AnniversaryParam anniversaryParam);

    CommonResult<List<AnniversaryResult>> getAnniversaries(Integer userId);

}
