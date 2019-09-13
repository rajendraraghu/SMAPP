import { Moment } from 'moment';

export interface IDeltaProcess {
  id?: number;
  name?: string;
  description?: string;
  type?: string;
  tablesList?: string;
  selectedColumns?: string;
  pk?: string;
  lastStatus?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  sourceConnectionName?: string;
  sourceConnectionId?: number;
  snowflakeConnectionName?: string;
  snowflakeConnectionId?: number;
  runBy?: string;
}

export class DeltaProcess implements IDeltaProcess {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public type?: string,
    public tablesList?: string,
    public selectedColumns?: string,
    public pk?: string,
    public lastStatus?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public sourceConnectionName?: string,
    public sourceConnectionId?: number,
    public snowflakeConnectionName?: string,
    public snowflakeConnectionId?: number,
    public runBy?: string
  ) {}
}
