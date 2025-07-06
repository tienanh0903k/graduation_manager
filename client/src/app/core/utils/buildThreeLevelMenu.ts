export function buildMenuTree(flatMenus: any[]): any[] {
  const clonedMenus = flatMenus.map(menu => ({ ...menu, items: [] }));
  const menuMap = new Map<number, any>();

  clonedMenus.forEach(menu => {
    menuMap.set(menu.id, menu);
  });

  const tree: any[] = [];

  clonedMenus.forEach(menu => {
    if (menu.route) {
      menu.routerLink = [menu.route];
    }

    if (menu.parentId == null) {
      tree.push(menu);
    } else {
      const parent = menuMap.get(menu.parentId);
      if (parent) {
        parent.items = parent.items || [];
        parent.items.push(menu);
      }
    }
  });

  // Sắp xếp đệ quy theo orderNo
  function sortMenus(menus: any[]) {
    menus.sort((a, b) => (a.orderNo ?? 999) - (b.orderNo ?? 999));
    menus.forEach(menu => {
      if (menu.items?.length) sortMenus(menu.items);
    });
  }

  sortMenus(tree);

  return tree;
}
