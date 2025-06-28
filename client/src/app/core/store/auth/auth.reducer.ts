import { createReducer, on } from '@ngrx/store';
import { login, loginSuccess, loginFailure, logout } from './auth.actions';
import { AuthenticationResponse } from '../../models/authentication-response.model';
import { UserDTO } from '../../models/user.model';
import { MenuPermissionResponseDTO } from '../../models/MenuPermission/MenuPermissionResponseDTO';

export interface AuthState {
  token: string | null;
  userDto: UserDTO | null;
  menuPermissions: MenuPermissionResponseDTO[] | null;
  message: string | null;
  isLoading: boolean;
  error: any;
}

export const initialState: AuthState = {
  token: null,
  userDto: null,
  menuPermissions: null,
  message: null,
  isLoading: false,
  error: null,
};

export const authReducer = createReducer(
  initialState,
  on(login, (state) => ({
    ...state,
    isLoading: true,
    error: null,
  })),
  on(loginSuccess, (state, { response }) => ({
    ...state,
    token: response.token,
    userDto: response.userDto,
    menuPermissions: response.menuPermissions,
    message: response.message,
    isLoading: false,
    error: null,
  })),
  on(loginFailure, (state, { error }) => ({
    ...state,
    isLoading: false,
    error: error,
  })),
  on(logout, () => initialState)
);
