package ca.utoronto.utm.mcs;

import dagger.Component;
import javax.inject.Singleton;

/**
 * PUT /api/v1/actorIDEXIST
 * @param:
 * @return:
 * - 200 OK: Actor exist
 * - 400 BAD REQUEST: If name/actorid are improperly formatted
 * - 500 INTERNAL SERVER ERROR: if error occured during a database operation
 */


@Singleton
// TODO Uncomment The Line Below When You Have Implemented ServerModule 
 @Component(modules = ServerModule.class)
public interface ServerComponent {

	public Server buildServer();
}
