package com.codegym.project.users.userProfile;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AvatarUploadForm {
    MultipartFile avatar;
}
