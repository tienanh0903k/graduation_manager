import { createAction, props } from '@ngrx/store';
import { AuthenticationResponse } from '../../models/authentication-response.model';
import { AuthenticationRequest } from '../../models/authentication-request.model';

export const login = createAction(
  '[Auth] Login',
  props<{ request: AuthenticationRequest }>()
);

export const loginSuccess = createAction(
  '[Auth] Login Success',
  props<{ response: AuthenticationResponse }>()
);

export const loginFailure = createAction(
  '[Auth] Login Failure',
  props<{ error: any }>()
);

export const logout = createAction('[Auth] Logout');
