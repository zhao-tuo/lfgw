import { BaseEntity } from './../../shared';

export class SaleAmt1 implements BaseEntity {
    constructor(
        public id?: number,
        public amt1Id?: number,
        public projectId?: number,
        public shopId?: number,
        public amt1Info?: string,
    ) {
    }
}
