package se.frisk.cadettsplittershistory_edufy.clients;

import se.frisk.cadettsplittershistory_edufy.dto.UserDTO;

public interface UserClient {

    UserDTO getUserById(Long userId);

    boolean userIsActive(Long userId);
}
