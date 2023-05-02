package com.nhom10.touringweb.repository;

import com.nhom10.touringweb.model.user.ImgAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgAvatarRepository  extends JpaRepository<ImgAvatar, Long> {

    ImgAvatar getImgAvatarByIdUser(int idUser);
}
