import { BaseEntity } from './../../shared';

export class ShopInfo implements BaseEntity {
    constructor(
        public id?: number,
        public shopId?: number,
        public projectId?: number,
        public shopName?: string,
    ) {
    }
}
