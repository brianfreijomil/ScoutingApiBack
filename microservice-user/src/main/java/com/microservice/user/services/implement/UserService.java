package com.microservice.user.services.implement;

public class UserService {

    /*
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllByTeamId(Long teamId) {
        return this.userRepository.findAllByTeamId(teamId)
                .stream()
                .map(user -> new UserResponseDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getByCredentials(LoginDTO loginDTO) {
        User user = this.userRepository.findByUsername(loginDTO.getUsername());
        if(user != null) {
            //SI CUMPLE LA CONTRASEÑA RETORNO EL USUARIO
            return new UserResponseDTO(user);
        }
        throw new NotFoundException("User","username",loginDTO.getUsername());
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateStatus(Boolean isEnabled, Long id) {
        Optional<User> userExisting = this.userRepository.findById(id);
        if(!userExisting.isEmpty()) {
            try {
                userExisting.get().setEnabled(isEnabled);

                this.userRepository.save(userExisting.get());
                return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("updateStatus","User","ID",id.toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("User","ID",id.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateAllUsersSubscriptionStatus(Boolean status, Long teamId) {
        try {
            this.userRepository.updateUserSubscriptionStatusByTeamId(status,teamId);
            return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
        }
        catch (Exception ex) {
            throw new ConflictPersistException(
                    "updateSubscriptionStatus",
                    "User",
                    "teamId",
                    teamId.toString(),
                    ex.getMessage());
        }
    }

     */

}
