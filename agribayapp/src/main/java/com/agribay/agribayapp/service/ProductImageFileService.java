package com.agribay.agribayapp.service;

import static org.apache.http.entity.ContentType.IMAGE_GIF;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.agribay.agribayapp.exception.SpringAgribayException;
import com.agribay.agribayapp.model.BucketName;
import com.agribay.agribayapp.model.User;

@Service
public class ProductImageFileService {
	private final S3FileStorageService s3FileStorageService;
	private final S3UsageService s3UsageService;

	@Autowired
	public ProductImageFileService(S3FileStorageService s3FileStorageService,
								   S3UsageService s3UsageService) {
		super();
		this.s3FileStorageService = s3FileStorageService;
		this.s3UsageService = s3UsageService;
	}

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new SpringAgribayException("Cannot upload empty file [ " + file.getSize() + "]");
        }
    }
    
    private void isImage(MultipartFile file) {
    	if (!Arrays.asList(
	          IMAGE_JPEG.getMimeType(),
	          IMAGE_PNG.getMimeType(),
	          IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
    		throw new SpringAgribayException("File must be an image [" + file.getContentType() + "]");
      }
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }
    
    private String validateAndStore(MultipartFile file, User seller) {
    	// Check if image is not empty
		this.isFileEmpty(file);
		
		// If file is an image
		this.isImage(file);
		
		// Grab some metadata from file if any
		Map<String, String> metadata = extractMetadata(file);
		
		// Set the path and filename
		String path = String.format("%s/%s", BucketName.PRODUCT_IMAGE.getBucketName(), seller.getUsername() + seller.getId());
		String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
		
		// Store the image in s3
		try {
		  s3FileStorageService.save(path, filename, Optional.of(metadata), file.getInputStream());
		} catch (IOException e) {
		  throw new SpringAgribayException(e.getMessage());
		}
		
		return filename;
    }

	public String[] getImageUrls(List<MultipartFile> imageFiles, User seller) {
		String imageUrls[] = new String[] {"", ""};
		if (imageFiles.size() == 0 || !s3UsageService.putRequestsIsWithinLimit()) {
			// if no files present or S3 usage is not within limit
			return imageUrls;
		}

		// for first file
		MultipartFile file1 = imageFiles.get(0);
		imageUrls[0] = this.validateAndStore(file1, seller);
		
		if (imageFiles.size() > 1) {
			// if second file is also present then
			MultipartFile file2 = imageFiles.get(1);
			imageUrls[1] = this.validateAndStore(file2, seller);
		}
		
		return imageUrls;
	}

    public byte[] downloadUserProfileImage(String sellerNameAndId, String filename) {
    	if (!s3UsageService.getRequestsIsWithinLimit()) {
    		// check if S3 get requests usage is within limit otherwise return empty byte[]
    		return new byte[0];
    	}
    	// path of bucket
    	String path = String.format("%s/%s",
                BucketName.PRODUCT_IMAGE.getBucketName(),
                sellerNameAndId
        );
    	// send exact path and filename to download the specified file
        return s3FileStorageService.download(path, filename);
    }
}
