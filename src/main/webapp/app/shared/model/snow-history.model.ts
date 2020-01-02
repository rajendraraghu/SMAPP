import { Moment } from 'moment';

export interface ISnowHistory {
  id?: number;
  name?: string;
  description?: string;
  type?: string;
  tablesToMigrate?: string;
  //   cdc?: string;
  //   bulk?: string;
  //   cdcPk?: string;
  //   bulkPk?: string;
  //   cdcCols?: string;
  lastStatus?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  // valid?: Boolean;
  sourceConnectionName?: string;
  sourceConnectionId?: number;
  snowflakeConnectionName?: string;
  snowflakeConnectionId?: number;
  runBy?: string;
}

export class SnowHistory implements ISnowHistory {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public type?: string,
    public tablesToMigrate?: string,
    // public cdc?: string,
    // public bulk?: string,
    // public bulkPk?: string,
    // public cdcPk?: string,
    // public cdcCols?: string,
    public lastStatus?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    // public valid?: Boolean,
    public sourceConnectionName?: string,
    public sourceConnectionId?: number,
    public snowflakeConnectionName?: string,
    public snowflakeConnectionId?: number,
    public runBy?: string
  ) {}
}
