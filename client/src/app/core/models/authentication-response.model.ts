import { User } from './user.model';
import { MenuPermissionResponseDTO } from './MenuPermission/MenuPermissionResponseDTO';

export interface AuthenticationResponse {
  token: string;
  userDto: User;
  menuPermissions: MenuPermissionResponseDTO[];
  message: string;
}
