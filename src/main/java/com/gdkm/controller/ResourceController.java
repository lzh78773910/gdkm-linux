package com.gdkm.controller;

import com.gdkm.dto.ResourceDto;
import com.gdkm.model.Resource;
import com.gdkm.model.ResourceType;
import com.gdkm.service.ResourceService;
import com.gdkm.service.ResourceTypeService;
import com.gdkm.utils.ResultVOUtil;
import com.gdkm.utils.UCloudProvider;
import com.gdkm.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Api(value = "前段教学资源展示", tags = "前段教学资源展示")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceTypeService resourceTypeService;

    @Autowired
    private UCloudProvider uCloudProvider;

    //资源类型展示

    /**
     * 资源类型展示分页反馈
     *
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("资源类型展示")
    @GetMapping("/resourceType/{page}/{size}")
    @ResponseBody
    public ResultVO showResourceType(
            @PathVariable(value = "page", required = false) Integer page,
            @PathVariable(value = "size", required = false) Integer size
    ) {
        Page<ResourceType> resourceTypePage = resourceTypeService.getPageSort(page, size);
        return ResultVOUtil.success(resourceTypePage);
    }

    //资源根据id展示

    /**
     * 资源根据id展示
     *
     * @param rtId
     * @return
     */
    @ApiOperation("资源类型根据Id查询")
    @GetMapping(value = "/resources/{rtId}")
    @ResponseBody
    public ResultVO showResourceTypeByRtId(
            @RequestParam(value = "rtId") Integer rtId
    ) {
        List<ResourceDto> resourceDtoList = resourceService.findResourceById(rtId);
        return ResultVOUtil.success(resourceDtoList);
    }


    //资源展示

    /**
     * 资源展示分页反馈
     *
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("资源展示")
    @GetMapping("/resources/{page}/{size}")
    @ResponseBody
    public ResultVO showResources(
            @PathVariable(value = "page", required = false) Integer page,
            @PathVariable(value = "size", required = false) Integer size
    ) {
        Page<Resource> resourcePage = resourceService.getPageSort(page, size);
        return ResultVOUtil.success(resourcePage);
    }

    //资源下载
    /**
     * 资源下载
     * @param resUrl
     * @return
     */
    @ApiOperation("资源下载")
    @GetMapping("/resource/download")
    @ResponseBody
    public ResultVO resourceDownload(@RequestParam(value = "resUrl") String resUrl) {
        uCloudProvider.getStream(resUrl);
        return ResultVOUtil.success();
    }

}
