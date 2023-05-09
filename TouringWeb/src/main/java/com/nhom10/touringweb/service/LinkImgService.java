package com.nhom10.touringweb.service;

import com.nhom10.touringweb.model.user.LinkImg;
import com.nhom10.touringweb.repository.LinkImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinkImgService {
    @Autowired
    LinkImgRepository linkImgRepository;


    public String getNameImgByIdTour(Long idTour) {
        return linkImgRepository.getLinKImgByIdTourAndLevel(idTour,0).getNameImg();
    }

    public List<String> getAllLinkImg(Long idTour) {
        List<LinkImg> list = linkImgRepository.getAllByIdTour(idTour);
        List<String> result = new ArrayList<>();
        for(LinkImg linkImg : list){
            result.add(linkImg.getNameImg());
        }
        return result;
    }
}
