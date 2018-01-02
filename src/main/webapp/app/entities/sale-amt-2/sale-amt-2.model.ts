import { BaseEntity } from './../../shared';

export class SaleAmt2 implements BaseEntity {
    constructor(
        public id?: number,
        public amt2Id?: number,
        public projectId?: number,
        public shopId?: number,
        public amt2Info?: string,
    ) {
    }
}
