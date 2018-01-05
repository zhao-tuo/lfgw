import { BaseEntity } from './../../shared';

export class ShopInfo implements BaseEntity {
    constructor(
        public id?: number,
        public shopId?: number,
        public shopName?: string,
        public projectInfoId?: number,
        public projectName?:string,
    ) {
    }
}
