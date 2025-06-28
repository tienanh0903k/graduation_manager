import { PermissionDTO } from './PermissionDTO';

export interface MenuPermissionResponseDTO {
  label: string;
  route: string;
  icon: string;
  permissions: PermissionDTO[];
}
