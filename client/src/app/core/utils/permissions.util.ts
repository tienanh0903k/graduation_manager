
export function isOwner<T>(
    item: T,
    getUserId: (item: T) => number,
    currentUserId: number
): boolean  {
      return getUserId(item) === currentUserId;
}
