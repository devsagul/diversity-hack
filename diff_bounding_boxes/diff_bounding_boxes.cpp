/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   diff_bounding_boxes.cpp                            :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: ShnurD6 <shnurd6@mail.ru>                  +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2019/09/07 17:33:44 by ShnurD6           #+#    #+#             */
/*   Updated: 2019/09/08 12:46:37 by ShnurD6          ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

#include "diff_bounding_boxes.hpp"

struct pixel
{
	pixel(data_type aData)
	{
		this->data = aData;
	}

	data_type	data;
	bool		visited = false;
};

std::vector<size_t> FillOneDiff(
	std::vector<std::vector<pixel>>& aFirstImage,
	std::vector<std::vector<data_type>>& aSecondImage,
	size_t aX,
	size_t aY)
{
	std::vector<size_t> result{aX, aY, aX, aY};
	std::queue<std::pair<size_t, size_t>> queue;
	aFirstImage[aX][aY].visited = true;

	queue.push({aX, aY});
	while (!queue.empty())
	{
		if (queue.front().first < result[d_x])
			result[d_x] = queue.front().first;
		else if (queue.front().first > result[d_x_len])
			result[d_x_len] = queue.front().first;
		if (queue.front().second < result[d_y])
			result[d_y] = queue.front().second;
		else if (queue.front().second > result[d_y_len])
			result[d_y_len] = queue.front().second;

		if (queue.front().first
			&& !aFirstImage[queue.front().first - 1][queue.front().second].visited
			&& aFirstImage[queue.front().first - 1][queue.front().second].data
				!= aSecondImage[queue.front().first - 1][queue.front().second])
		{
			queue.push({queue.front().first - 1, queue.front().second});
			aFirstImage[queue.front().first - 1][queue.front().second].visited = true;
		}

		if (queue.front().second
			&& !aFirstImage[queue.front().first][queue.front().second - 1].visited
			&& aFirstImage[queue.front().first][queue.front().second - 1].data
				!= aSecondImage[queue.front().first][queue.front().second - 1])
		{
			queue.push({queue.front().first, queue.front().second - 1});
			aFirstImage[queue.front().first][queue.front().second - 1].visited = true;
		}

		if (queue.front().first + 1 < aFirstImage.size() 
			&& !aFirstImage[queue.front().first + 1][queue.front().second].visited
			&& aFirstImage[queue.front().first + 1][queue.front().second].data
				!= aSecondImage[queue.front().first + 1][queue.front().second])
		{
			queue.push({queue.front().first + 1, queue.front().second});
			aFirstImage[queue.front().first + 1][queue.front().second].visited = true;
		}

		if (queue.front().second + 1 < aFirstImage.size() 
			&& !aFirstImage[queue.front().first][queue.front().second + 1].visited
			&& aFirstImage[queue.front().first][queue.front().second + 1].data
				!= aSecondImage[queue.front().first][queue.front().second + 1])
		{
			queue.push({queue.front().first, queue.front().second + 1});
			aFirstImage[queue.front().first][queue.front().second + 1].visited = true;
		}

		queue.pop();
	}

	result[d_x_len] -= result[d_x];
	result[d_y_len] -= result[d_y];

	return result; 
}

std::vector<std::vector<size_t>> DiffBoudingBoxes(
	std::vector<std::vector<pixel>>& aFirstImage,
	std::vector<std::vector<data_type>>& aSecondImage)
{
	std::vector<std::vector<size_t>> result;

	ft_assert(aFirstImage.size(), "first_image size == 0");
	ft_assert(aFirstImage.size() == aSecondImage.size() && aFirstImage[0].size() == aSecondImage[0].size(), "first_image size != second_image size");
	for (size_t i = 0; i < aFirstImage.size(); i++)
	{
		for (size_t j = 0; j < aFirstImage[0].size(); j++)
		{
			if (!aFirstImage[i][j].visited
				&& aFirstImage[i][j].data != aSecondImage[i][j])
			{
				result.push_back(FillOneDiff(aFirstImage, aSecondImage, i, j));
			}
		}
	}
	return result;
}

std::vector<std::vector<pixel>> ProcessImageToPixels(
	std::vector<std::vector<data_type>>& aImage)
{
	std::vector<std::vector<pixel>> result;
	
	for (size_t i = 0; i < aImage.size(); i++)
	{
		result.push_back(std::vector<pixel>());
		for (size_t j = 0; j < aImage[0].size(); j++)
		{
			result[i].push_back(pixel(aImage[i][j]));
		}
	}
	return result;
}

std::vector<std::vector<pixel>> ProcessNmpArrToPixels(
	Ndarray<data_type, 2>& aImage)
{
	std::vector<std::vector<pixel>> result;
	
	for (long i = 0; i < aImage.getShape(0); i++)
	{
		result.push_back(std::vector<pixel>());
		for (long j = 0; j < aImage.getShape(1); j++)
		{
			result[i].push_back(pixel(aImage[i][j]));
		}
	}
	return result;
}

std::vector<std::vector<data_type>> ProcessNmpArrToVector(
	Ndarray<data_type, 2>& aImage)
{
	std::vector<std::vector<data_type>> result;
	
	for (long i = 0; i < aImage.getShape(0); i++)
	{
		result.push_back(std::vector<data_type>());
		for (long j = 0; j < aImage.getShape(1); j++)
		{
			result[i].push_back(aImage[i][j]);
		}
	}
	return result;
}

void test()
{
	std::vector<std::vector<data_type>> firstImage = 
	{
		{0, 0, 0, 0},
		{0, 0, 0, 0},
		{0, 0, 0, 0},
		{0, 0, 0, 0},
	};

	std::vector<std::vector<data_type>> secondImage = 
	{
		{0, 0, 1, 0},
		{0, 0, 1, 0},
		{1, 1, 1, 1},
		{0, 0, 1, 0},
	};

	auto firstImage_converted = ProcessImageToPixels(firstImage);

	for (auto& field: DiffBoudingBoxes(firstImage_converted, secondImage))
	{
		std::cout << "x = " << field[d_x] << ", y = " << field[d_y] << ", len_x = " << field[d_x_len] << ", len_y = " << field[d_y_len] << std::endl;
	}
}

// int main()
// {
// 	test();
// 	return (0);

// }
PyObject* CppAnswers_To_Python(const std::vector<std::vector<size_t>> &data)
{
	PyObject* listObj = PyList_New(data.size());
	ft_assert(listObj, "Can\'t allocate memoty to PyList");
	for (size_t i = 0; i < data.size(); i++)
	{
		PyObject* listSubObj = PyList_New(data[i].size());
		for (size_t j = 0; j < data[i].size(); j++)
		{
			PyObject *num = PyFloat_FromDouble(data[i][j]);
			ft_assert(num, "Can\'t allocate memory to PyFloat");
			PyList_SET_ITEM(listSubObj, j, num);
		}
		PyList_SET_ITEM(listObj, i, listSubObj);
	}
	return listObj;
}

extern "C"
{

// Это та самая ф-ция, к которой нужно стучаться
static PyObject *DbbWrapper(numpyArray<data_type> array1, numpyArray<data_type> array2)
{
	Ndarray<data_type, 2> a(array1);
	Ndarray<data_type, 2> b(array2);

	std::vector<std::vector<pixel>> firstImage = ProcessNmpArrToPixels(a);
	std::vector<std::vector<data_type>> secondImage = ProcessNmpArrToVector(b);

	auto result = DiffBoudingBoxes(firstImage, secondImage);	

	return CppAnswers_To_Python(result);
}

} // end extern "C"

// static PyMethodDef TcoMethods[] =
// {
// 	{ "DiffBoundingBoxes", DbbWrapper, METH_VARARGS, "Diff bounding boxes. Input: 1) FirstImage[[]] (numpyArray<npy_uint8>), 2) SecondImage[[]] (numpyArray<npy_uint8>)" },
// 	{ NULL, NULL, 0, NULL }
// };

// static PyObject* DbbWrapper(PyObject* self, PyObject* args)
// {
// 	PyObject *input;
// 	PyArrayObject *arr;
// 	double *dptr;
// 	long int *dims;
// 	long int nd;

// 	if (!PyArg_ParseTuple(args, "O", &input))
// 		return NULL;

// 	/* Вот тут может быть пиздец, если что-то не работает -- вероятнее всего проблема тут) */
// 	arr = (PyArrayObject*)PyArray_FROM_OTF(input, NPY_UINT8, 0);
// 	if (arr == NULL)
// 		return NULL;

// 	nd = PyArray_NDIM(arr);
// 	dims = PyArray_DIMS(arr);
// 	if ((nd != 1) || (dims[0] < 1)) {
// 		Py_XDECREF(arr);
// 		return NULL;
// 	}
// 	dptr = (double *)PyArray_DATA(arr);

// 	Py_DECREF(arr);

// 	return Py_BuildValue("d", 0);
// }