package com.gdkm.controller;

import com.gdkm.Repository.TotalVisitsRepostory;
import com.gdkm.dto.HomeLabelDto;
import com.gdkm.model.LabelTitle;
import com.gdkm.service.HomeLebelService;
import com.gdkm.service.TotalVisitsService;
import com.gdkm.utils.ResultVOUtil;
import com.gdkm.vo.LabelVO;
import com.gdkm.vo.ResultVO;
import com.gdkm.vo.TitleListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Api(value = "主页", tags = "主页")
@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeLebelService homeLebelService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private TotalVisitsService totalVisitsService;


    @ApiOperation(value = "每一天访问网站的数量")
    @ResponseBody
    @GetMapping("/cout")
    public ResultVO cout(){
        Set ipSet = (Set) servletContext.getAttribute("ipSet");
        return ResultVOUtil.success(ipSet.size());
    }
    @ApiOperation(value = "网站的总数量")
    @ResponseBody
    @GetMapping("/totalcout")
    public ResultVO totalcout(){
        Set ipSet = (Set) servletContext.getAttribute("ipSet");
        long count = totalVisitsService.countAll();
        count+=ipSet.size();
        return ResultVOUtil.success(count);
    }

    @ApiOperation(value = "标签和内容")
    @ResponseBody
    @GetMapping("/label")
    public List<LabelVO> Label(){
        List<HomeLabelDto> homeLabelDtoList=homeLebelService.list();
        List<LabelVO> labelVOList=new ArrayList<>();

        for (HomeLabelDto homeLabelDto:homeLabelDtoList){
            LabelVO labelVO=new LabelVO();
            List<String> list=new ArrayList<>();

            labelVO.setHomeLabel(homeLabelDto.getHomeLabel());
            labelVO.setLabelImg(homeLabelDto.getLabelImg());
            for (LabelTitle labelTitle: homeLabelDto.getLabelTitleList()){
                list.add(labelTitle.getTitle());
            }
            labelVO.setList(list);
            labelVOList.add(labelVO);
        }
        return labelVOList;
    }
}
