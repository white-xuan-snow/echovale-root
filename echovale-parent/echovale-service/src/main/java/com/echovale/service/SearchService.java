package com.echovale.service;


import com.echovale.service.vo.SearchVO;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {
    SearchVO neteaseSearch(String content, Integer limit, Integer offset, Integer type) throws Exception;
}
