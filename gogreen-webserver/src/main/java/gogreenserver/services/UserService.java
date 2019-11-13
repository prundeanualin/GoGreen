package gogreenserver.services;

import gogreenserver.entity.User;
import gogreenserver.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bcryptPasswordEncoder;
    private HttpServletRequest req;

    /**
     * Autowired constructor. What else is there to say?
     */
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder,
            HttpServletRequest req) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.req = req;
    }

    public User createUser(User user) {
        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(String theUserName) {
        return userRepository.findById(theUserName);
    }

    public void deleteUser(String user) {
        userRepository.deleteById(user);
    }

    /**.
     * converting the multipartfile recieved by http request into an image and
     * writing this image inside profile_pictures folder in server side
     * 
     * @param file the photo to save on disk
     * @throws IOException image could not be written
     */

    public void saveProfilePicture(MultipartFile file, String userName) throws IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        String pathname = "gogreen-webserver/src/main/profile_pictures/";
        File imgLoc = new File(pathname
                + userName + ".png");
        imgLoc.createNewFile();
        ImageIO.write(image, "png", imgLoc);
    }

}