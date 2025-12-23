package com.example.restourant.config;

import com.example.restourant.model.Pelayan; // IMPORT INI
import com.example.restourant.model.User;
import com.example.restourant.repository.PelayanRepository;
import com.example.restourant.repository.UserRepository;
import jakarta.annotation.PostConstruct; // IMPORT INI
import lombok.RequiredArgsConstructor; // IMPORT INI, pastikan benar
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final PelayanRepository pelayanRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        initPelayan();
        initUsers();
    }

    private void initPelayan() {
        if (pelayanRepository.count() == 0) {
            Pelayan pelayan1 = new Pelayan();
            pelayan1.setNama("Pelayan 1");
            pelayan1.setNomorTelepon("08123456789");
            pelayan1.setAktif(true);
            pelayan1.setGaji(2500000.0);
            pelayanRepository.save(pelayan1);

            Pelayan pelayan2 = new Pelayan();
            pelayan2.setNama("Pelayan 2");
            pelayan2.setNomorTelepon("08198765432");
            pelayan2.setAktif(true);
            pelayan2.setGaji(2500000.0);
            pelayanRepository.save(pelayan2);

            System.out.println("Data pelayan berhasil diinisialisasi");
        }
    }

    private void initUsers() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@restaurant.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNama("Administrator");
            admin.setAktif(true);
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setEmail("user@restaurant.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setNama("Regular User");
            user.setAktif(true);
            userRepository.save(user);

            System.out.println("Data users berhasil diinisialisasi");
        }
    }
}