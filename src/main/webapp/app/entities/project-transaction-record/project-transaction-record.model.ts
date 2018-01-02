import { BaseEntity } from './../../shared';

export class ProjectTransactionRecord implements BaseEntity {
    constructor(
        public id?: number,
        public recordId?: number,
        public projectId?: number,
        public transactionYear?: number,
    ) {
    }
}
