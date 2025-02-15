package com.example.userservice.mapper;

import com.example.userservice.dto.request.SignUpRequest;
import com.example.userservice.dto.response.*;
import com.example.userservice.dto.response.FriendService.FriendshipResponse;
import com.example.userservice.entity.Account;
import com.example.userservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { RolePermissionMapper.class })
public interface AccountMapper {
    Account toAccount(SignUpRequest signUpRequest);

    // Ánh xạ thủ công
    default AccountResponse toAccountResponse(Account account) {
        if (account == null) {
            return null;
        }

        // Ánh xạ các trường từ Account sang AccountResponse
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setIdAccount(account.getIdAccount());
        accountResponse.setEmail(account.getEmail());
        accountResponse.setFirstName(account.getFirstName());
        accountResponse.setLastName(account.getLastName());
        accountResponse.setPhoneNumber(account.getPhoneNumber());
        accountResponse.setDateOfBirth(account.getDateOfBirth());
        accountResponse.setAddress(account.getAddress());
        accountResponse.setImgAvatar(account.getImgAvatar());

        // Ánh xạ Role sang RoleResponse
        if (account.getRole() != null) {
            Role role = account.getRole();
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setIdRole(role.getIdRole());
            roleResponse.setRoleName(role.getRoleName());

            // Ánh xạ danh sách RolePermission sang RolePermissionResponse (nếu có)
            if (role.getRolePermissonList() != null) {
                List<RolePermissionResponse> rolePermissionResponses = role.getRolePermissonList()
                        .stream()
                        .map(rolePermission -> {
                            RolePermissionResponse response = new RolePermissionResponse();
                            response.setIdPermission(rolePermission.getPermission().getIdPermission());
                            response.setPermissionName(rolePermission.getPermission().getPermissionName());
                            response.setAction(rolePermission.getAction());
                            return response;
                        })
                        .collect(Collectors.toList());

                roleResponse.setRolePermissionList(rolePermissionResponses);
            }

            accountResponse.setRole(roleResponse);
        }

        return accountResponse;
    }

    AccountBasicResponse toAccountBasicResponse(Account account);
    @Mapping(source = "accountBasicResponse", target = "accountBasicResponse")
    @Mapping(source = "friendshipResponse", target = "friendshipResponse")
    AccountRelationResponse toAccountRelationResponse(AccountBasicResponse accountBasicResponse, FriendshipResponse friendshipResponse);



}
