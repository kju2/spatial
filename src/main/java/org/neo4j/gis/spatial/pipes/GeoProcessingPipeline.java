/**
 * Copyright (c) 2002-2011 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.gis.spatial.pipes;

import java.util.Iterator;

import org.neo4j.gis.spatial.Layer;
import org.neo4j.gis.spatial.SpatialDatabaseRecord;
import org.neo4j.gis.spatial.pipes.processing.Buffer;
import org.neo4j.gis.spatial.pipes.processing.ToDensityIslands;
import org.neo4j.gis.spatial.pipes.processing.ToOuterLinearRing;
import org.neo4j.gis.spatial.pipes.processing.ToPointsPipe;

import com.tinkerpop.pipes.util.FluentPipeline;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

public class GeoProcessingPipeline<S, E> extends FluentPipeline<S, E>
{

    private final Layer layer;

    public GeoProcessingPipeline( Layer layer )
    {
        this.layer = layer;
    }

    public FluentPipeline<SpatialDatabaseRecord, Point> toPoints()
    {
        return this.add(new ToPointsPipe());
    }

    public FluentPipeline<Geometry, Geometry> toOutherLinearRing()
    {
        return this.add(new ToOuterLinearRing());
    }
    
    public FluentPipeline<Geometry, Geometry> buffer(double distance)
    {
        return this.add(new Buffer(distance));
    }
    
    public FluentPipeline<Geometry, Geometry> toDensityIslands(double density)
    {
        return this.add(new ToDensityIslands<S, E>(density));
    }
    
    public long countPoints()
    {
        return GeoPipeHelper.counter( (Iterator<SpatialDatabaseRecord>) this );
    }
    
    public Layer getLayer() 
    {
    	return this.layer;
    }

}