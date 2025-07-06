import { createFeatureSelector, createSelector } from '@ngrx/store';
import { AuthState } from './auth.reducer';

export const selectAuthState = createFeatureSelector<AuthState>('auth');

export const selectAuthToken = createSelector(
  selectAuthState,
  (state) => state.token
);

export const selectAuthUser = createSelector(
  selectAuthState,
  (state) => state.userDto
);

export const selectUserRole = createSelector(
  selectAuthUser,
  (user) => user?.role
);

export const selectAuthMenuPermissions = createSelector(
  selectAuthState,
  (state) => state.menuPermissions
);

export const selectAuthIsLoading = createSelector(
  selectAuthState,
  (state) => state.isLoading
);

export const selectAuthError = createSelector(
  selectAuthState,
  (state) => state.error
);
