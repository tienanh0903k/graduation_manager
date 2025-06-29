export function buildMenuTree(flatMenus: any[]): any[] {
  const clonedMenus = flatMenus.map(menu => ({ ...menu, items: [] }));
  const menuMap = new Map<number, any>();

  clonedMenus.forEach(menu => {
    menuMap.set(menu.id, menu);
  });

  const tree: any[] = [];

  clonedMenus.forEach(menu => {
    if (menu.parentId === null) {
      tree.push(menu);
    } else {
      const parent = menuMap.get(menu.parentId);
      if (parent) {
        parent.items.push(menu);
      }
    }
  });

  return tree;
}
