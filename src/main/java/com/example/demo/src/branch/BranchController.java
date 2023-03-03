package com.example.demo.src.branch;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.branch.model.*;
import com.example.demo.src.event.model.GetEventRes;
import com.example.demo.src.movie.model.GetMovieRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app")
public class BranchController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BranchProvider branchProvider;

    public BranchController(BranchProvider branchProvider){
        this.branchProvider = branchProvider;
    }

    /*
    * 지점 전체 조회 API
    * [GET] /branches
    * 지점 및 지역별 검색 조회 API
    * [GET] /branches?locationIdx=
    * @return BaseResponse<List<GetBranchRes>>
    */
    @ResponseBody
    @GetMapping("/branches/search")
    public BaseResponse<Map> getSearchBranch(@RequestParam(required = false) String search){
        try{
            if(search == null){search = "";}
            List<GetBranchRes> getBranchRes = branchProvider.getBranchesSearch(search);
            List<GetSearchRes> getSearchRes = branchProvider.getSearch(search);

            Map<String, Object> result = new LinkedMultiValueMap();
            result.put("search", getSearchRes);
            result.put("branch", getBranchRes);
            return new BaseResponse<>(result);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/branches") // [GET]/app/branches, [GET]/app/branches?locationIdx=?
    public BaseResponse<List<GetBranchRes>> getBranches(@RequestParam(required = false) Integer locationIdx){
        try{
            if(locationIdx == null){
                List<GetBranchRes> getBranchRes = branchProvider.getBranches();
                return new BaseResponse<>(getBranchRes);
            }
            List<GetBranchRes> getBranchRes = branchProvider.getBranchesByLocation(locationIdx);
            return new BaseResponse<>(getBranchRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
    * 지역 조회 API
    * [GET] /app/locations
    * @return BaseResponse<List<GetLocationRes>>
    */
    @ResponseBody
    @GetMapping("/locations")
    public BaseResponse<List<GetLocationRes>> getLocations(){
        try{
            List<GetLocationRes> getLocationRes = branchProvider.getLocations();
            return new BaseResponse<>(getLocationRes);
        }
        catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
