export function filterMenuByPermissions(menuConfig: any[], userPermissions: any[] | null): any[] {
  if (!userPermissions) return [];

  return menuConfig
    .filter(item => {
      const match = userPermissions.find(p => p.route === item.route && p.permissions[0]?.canRead);
      return !!match;
    })
    .map(menu => ({
      ...menu,
      items: menu.children || []
    }));
}
