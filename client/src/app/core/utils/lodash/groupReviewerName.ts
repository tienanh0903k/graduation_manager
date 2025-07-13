import _ from 'lodash';

export function groupReviewerName(rawList: any[]): any[] {
  return _(rawList)
    .groupBy('id')
    .map(list => ({
      ...list[0],
      reviewerName: [
        _(list)
          .map('reviewerName')
          .filter(Boolean)
          .uniq()
          .join(', ')
      ]
    }))
    .value();
}
