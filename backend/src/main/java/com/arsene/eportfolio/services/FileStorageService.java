package com.arsene.eportfolio.services;

import com.arsene.eportfolio.model.data.DBFileRepository;
import com.arsene.eportfolio.model.entities.DBFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageService {

    private final DBFileRepository dbFileRepository;

    public FileStorageService(DBFileRepository dbFileRepository) {
        this.dbFileRepository = dbFileRepository;
    }

    public DBFile store(MultipartFile file) {

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new IllegalArgumentException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile();
            dbFile.setName(fileName);
            dbFile.setType(file.getContentType());
            dbFile.setData(file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not store file " + fileName + ". Please try again!", ex);
        }

    }

    public DBFile retrieve(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("No file with such Id"));
    }
}
