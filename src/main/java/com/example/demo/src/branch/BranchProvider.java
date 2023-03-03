package com.example.demo.src.branch;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.branch.model.*;
import com.example.demo.src.event.model.GetEventRes;
import com.example.demo.src.movie.model.GetMovieRes;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.AES128;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.firebase.database.DatabaseException;
import org.hibernate.dialect.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.LinkedMultiValueMap;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BranchProvider {

    private final BranchDao branchDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BranchProvider(BranchDao branchDao){
        this.branchDao = branchDao;
    }

    // 지점 검색
    public List<GetBranchRes> getBranchesSearch(String search) throws BaseException{
        try{
            List<GetBranchRes> getBranchRes = branchDao.searchBranches(search);
            return getBranchRes;
        }
        catch(Exception exception){
            System.out.println(exception.toString());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    // transactional search Response

//    @Transactional
    @Transactional(rollbackOn = {RuntimeException.class, SQLException.class, DatabaseException.class, Exception.class})
    public List<GetSearchRes> getSearch(String search) throws BaseException {
        List<GetSearchRes> getSearchRes = null;
        String s2 = "rollback";
        try {
            if(search.equals(s2)){
                System.out.println("error!!"+search);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new Exception();
            }
            else{
                System.out.println("not error!!"+search);
                if(search != ""){
                    PostSearchReq postSearchReq = new PostSearchReq(search);
                    branchDao.createSearch(postSearchReq);
                }
                getSearchRes = branchDao.getSearch();
            }
            return getSearchRes;
        }catch(Exception e){
            throw new BaseException(TRANSACTION_ERROR);
        }
    }


    public List<GetBranchRes> getBranches() throws BaseException{
        try{
            List<GetBranchRes> getBranchRes = branchDao.getBranches();
            return getBranchRes;
        }
        catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<GetLocationRes> getLocations() throws BaseException{
        try{
            List<GetLocationRes> getLocationRes = branchDao.getLocations();
            return getLocationRes;
        }
        catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<GetBranchRes> getBranchesByLocation(int locationIdx) throws BaseException{
        try{
            List<GetBranchRes> getBranchRes = branchDao.getBranchesByLocation(locationIdx);
            return getBranchRes;
        }
        catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
