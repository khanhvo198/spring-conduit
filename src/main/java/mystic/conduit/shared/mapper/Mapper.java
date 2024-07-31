package mystic.conduit.shared.mapper;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.user.dto.UserDto;
import mystic.conduit.domain.user.entity.UserEntity;
import mystic.conduit.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Mapper {
    private final ModelMapper mapper;
    private final JwtUtils jwtUtils;

    public UserDto mapToUserResponse(UserEntity userEntity) {
        UserDto response = mapper.map(userEntity, UserDto.class);
        response.setToken(jwtUtils.encode(response.getEmail()));
        return response;
    }


    public <T,S> S mapToResponse (T source, Class<S> type) {
        return mapper.map(source, type);
    }
}
