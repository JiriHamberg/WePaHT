/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.authentication;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jiri
 */
public interface UserRepository extends JpaRepository<User, Long>{
    public Long countByNameAndPassword(String name, String password);
}
