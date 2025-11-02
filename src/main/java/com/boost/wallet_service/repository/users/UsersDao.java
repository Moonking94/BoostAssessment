package com.boost.wallet_service.repository.users;

import com.boost.wallet_service.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersDao extends JpaRepository<UsersEntity, UUID>, UsersDaoCustom {

    Optional<UsersEntity> findByEmail(String email);

}
