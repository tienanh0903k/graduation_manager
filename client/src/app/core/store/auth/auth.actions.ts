import { createAction, props } from '@ngrx/store';
import { AuthenticationResponse } from '../../models/authentication-response.model';
import { AuthenticationRequest } from '../../models/authentication-request.model';

export const login = createAction('[Auth] Login', props<{ request: AuthenticationRequest }>());

export const loginSuccess = createAction('[Auth] Login Success', props<{ response: AuthenticationResponse }>());

export const loginFailure = createAction('[Auth] Login Failure', props<{ error: any }>());

export const autoLogin = createAction('[Auth] Tự động đăng nhập');
export const autoLoginFailure = createAction('[Auth] Tự động đăng nhập thất bại');

export const logout = createAction('[Auth] Logout');
