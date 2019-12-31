package com.gdkm.controller;

import com.gdkm.dto.VideoCommentDto;
import com.gdkm.dto.VideoDto;
import com.gdkm.model.Video;
import com.gdkm.model.VideoComment;
import com.gdkm.model.VideoItem;
import com.gdkm.service.VideoService;
import com.gdkm.utils.ResultVOUtil;
import com.gdkm.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "教学视频模块", tags = "教学视频模块")

public class VideoController {
    @Autowired
    private VideoService videoService;

    //教学视频展示
    /**
     * 分页反馈
     *
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("视频展示")
    @GetMapping("/video/{page}/{size}")
    public ResultVO showVideo(
            @PathVariable(value = "page", required = false) Integer page,
            @PathVariable(value = "size", required = false) Integer size
    ) {
        Page<Video> videoDtoPage = videoService.getPage(page, size);
        return ResultVOUtil.success(videoDtoPage);
    }

    //教学详情展示
    /**
     * 视频详情页面面list
     * @param videoId
     * @return
     */
    @ApiOperation("视频详情页面List")
    @GetMapping("/videoDetail/{videoId}")
    public ResultVO videoList(@PathVariable(value = "videoId") Integer videoId) {
        List<VideoItem> videoDto = videoService.listByVid(videoId);
        return ResultVOUtil.success(videoDto);
    }


    //教学讨论展示
    /**
     * 视频讨论
     * @param parentId
     * @return
     */
    @ApiOperation("视频讨论")
    @GetMapping("/videoComment/{parentId}")
    public ResultVO commentByVid(@PathVariable(value = "parentId") Integer parentId) {
        List<VideoCommentDto> videoComment = videoService.commentByVid(parentId);
        return ResultVOUtil.success(videoComment);
    }

    //教学讨论添加
    /**
     * 视频讨论添加
     * @param parentId
     * @param vcContent
     * @return
     * */
    @ApiOperation("视频讨论添加")
    @PostMapping("/byuser/videoCommentAdd")
    public ResultVO add(Integer parentId, String vcContent) {
        return ResultVOUtil.success(videoService.addVideoComment(parentId,vcContent));
    }
}
