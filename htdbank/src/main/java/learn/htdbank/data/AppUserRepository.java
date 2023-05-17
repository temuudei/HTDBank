package learn.htdbank.data;

import learn.htdbank.models.AppUser;

public interface AppUserRepository {
     AppUser findByUsername(String username);

     AppUser create(AppUser user);

     void update(AppUser user);
}
